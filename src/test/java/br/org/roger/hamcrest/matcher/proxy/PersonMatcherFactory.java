package br.org.roger.hamcrest.matcher.proxy;

import static br.org.roger.hamcrest.matcher.proxy.MatcherFactory.aProxy;

import br.org.roger.hamcrest.matcher.PersonMatcher;

public class PersonMatcherFactory {

	public static PersonMatcher aPerson() {
		return aProxy(PersonMatcher.class);
	}
}
