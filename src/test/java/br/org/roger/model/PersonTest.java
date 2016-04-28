package br.org.roger.model;

import static br.org.roger.hamcrest.matcher.proxy.PersonMatcherFactory.aPerson;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.lessThan;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PersonTest {

	private Person person;
	
	@Before
	public void setUp() {
		person = new Person();
	}
	
	@Test
	public void shouldMatchCriteriaHamcrestWay() {
		person.setName("Bruce Wayne");
		person.setAge(15);
		
		assertThat(person, allOf(
			    hasProperty("name", containsString("Wayne")),
			    hasProperty("age", lessThan(20))));
	}
	
	@Test
	public void shouldMatchCriteriaDynamicProxyWay() {
		person.setName("Bruce Wayne");
		person.setAge(15);
		List<String> options = Arrays.asList(new String[] {"1", "2", "3"});
		person.setOptions(options);
		
		assertThat(person, aPerson()
			    .withName("Bruce Wayne")
			    .withAge(lessThan(20))
			    .withOptions(Arrays.asList(new String[] {"1", "2", "3"})));
	}
}
