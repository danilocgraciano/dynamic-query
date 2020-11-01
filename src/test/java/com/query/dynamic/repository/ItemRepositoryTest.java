package com.query.dynamic.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.query.dynamic.builder.ItemBuilder;
import com.query.dynamic.builder.UnitBuilder;
import com.query.dynamic.entity.Item;
import com.query.dynamic.entity.Unit;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class ItemRepositoryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private ItemRepository repository;

	private Unit unit;

	@Before
	public void setup() {
		this.unit = new UnitBuilder().withDefaults().build();
		em.persist(unit);
	}

	@Test
	public void shouldCreate() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);

		em.persist(item);

		em.flush();
		em.clear();

		Optional<Item> created = repository.findById(item.getSku());
		assertTrue(created.isPresent());

	}

	@Test
	public void shouldUpdate() {
		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);
		
		var oldName = item.getName();

		em.persist(item);

		item.setName("new name");

		em.persist(item);

		em.flush();
		em.clear();

		var filter = new Item();
		filter.setName(oldName);

		var oldData = repository.find(filter, null);
		var updatedData = repository.find(item, null);

		assertTrue(oldData.isEmpty());
		assertFalse(updatedData.isEmpty());

	}

	@Test
	public void shouldFindAll() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);

		em.persist(item);

		em.flush();
		em.clear();

		var data = repository.find(null, null);
		assertTrue(data.getContent().size() == 1);

	}

	@Test
	public void shouldFindByName() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);

		em.persist(item);

		em.flush();
		em.clear();

		var filter = new Item();
		filter.setName(item.getName());

		var data = repository.find(filter, null);
		assertTrue(data.getContent().size() == 1);

	}

	@Test
	public void shouldFindByUnit() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);

		em.persist(item);

		em.flush();
		em.clear();

		var filter = new Item();
		filter.setUnit(this.unit);

		var data = repository.find(filter, null);
		assertTrue(data.getContent().size() == 1);

	}

	@Test
	public void shouldDelete() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);

		em.persist(item);

		em.flush();
		em.clear();

		repository.delete(item);

		var data = repository.find(null, null);
		assertTrue(data.getContent().isEmpty());

	}

	@Test(expected = PersistenceException.class)
	public void shouldNotCreateWithoutSku() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);
		item.setSku(null);

		em.persist(item);

		em.flush();
		em.clear();

	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldNotCreateWithoutName() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);
		item.setName(null);

		em.persist(item);

		em.flush();
		em.clear();

	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldNotCreateWithoutUnitPrice() {

		var item = new ItemBuilder().withDefaults().build();
		item.setUnit(this.unit);
		item.setUnitPrice(null);

		em.persist(item);

		em.flush();
		em.clear();

	}

	@Test(expected = PersistenceException.class)
	public void shouldNotCreateWithoutUnit() {

		var item = new ItemBuilder().withDefaults().build();
		em.persist(item);

		em.flush();
		em.clear();
		
		assertTrue(true);

	}

}
