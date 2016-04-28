package br.org.roger.hamcrest.matcher;

import java.util.List;

import org.hamcrest.Matcher;

import br.org.roger.model.Person;

public interface PersonMatcher extends Matcher<Person> {

	PersonMatcher withName(String expected);
    PersonMatcher withName(Matcher<? super String> matching);
    PersonMatcher withAge(int expected);
    PersonMatcher withAge(Matcher<Integer> matching);
    PersonMatcher withOptions(List<String> options);
    
}
