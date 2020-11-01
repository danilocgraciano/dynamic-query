package com.query.dynamic.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.query.dynamic.builder.UnitBuilder;
import com.query.dynamic.entity.Unit;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
public class UnitRepositoryTest {

	@Autowired
	private TestEntityManager em;

	@Autowired
	private UnitRepository repository;

	@Test
	public void shouldCreate() {

		var unit = new UnitBuilder().withDefaults().build();
		em.persist(unit);

		em.flush();
		em.clear();

		Optional<Unit> created = repository.findById(unit.getId());
		assertTrue(created.isPresent());

	}

	@Test
	public void shouldUpdate() {

		var unit = new UnitBuilder().withDefaults().build();
		var oldDescription = unit.getDescription();

		em.persist(unit);

		unit.setDescription("new description");

		em.persist(unit);

		em.flush();
		em.clear();

		var filter = new Unit();
		filter.setDescription(oldDescription);

		var oldData = repository.find(filter, null);
		var updatedData = repository.find(unit, null);

		assertTrue(oldData.isEmpty());
		assertFalse(updatedData.isEmpty());

	}

	@Test
	public void shouldFindAll() {

		var unit = new UnitBuilder().withDefaults().build();
		em.persist(unit);

		em.flush();
		em.clear();

		var data = repository.find(null, null);
		assertTrue(data.getContent().size() == 1);

	}

	@Test
	public void shouldFindByDescription() {

		var unit = new UnitBuilder().withDefaults().build();
		em.persist(unit);

		em.flush();
		em.clear();

		var filter = new Unit();
		filter.setDescription(unit.getDescription());

		var data = repository.find(filter, null);
		assertTrue(data.getContent().size() == 1);

	}

	@Test
	public void shouldDelete() {

		var unit = new UnitBuilder().withDefaults().build();
		em.persist(unit);

		em.flush();
		em.clear();

		repository.delete(unit);

		var data = repository.find(null, null);
		assertTrue(data.getContent().isEmpty());

	}

	@Test(expected = PersistenceException.class)
	public void shouldNotCreateWithoutId() {

		var unit = new UnitBuilder().withDefaults().build();
		unit.setId(null);
		em.persist(unit);

		em.flush();
		em.clear();

	}

	@Test(expected = ConstraintViolationException.class)
	public void shouldNotCreateWithoutDescription() {

		var unit = new UnitBuilder().withDefaults().build();
		unit.setDescription(null);
		em.persist(unit);

		em.flush();
		em.clear();

	}

}
