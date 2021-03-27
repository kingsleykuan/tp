package seedu.address.model.filter;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

public class PersonFilter implements Predicate<Person> {
    private final Set<Predicate<Name>> nameFilters;

    private Predicate<Name> composedNameFilter;

    /**
     * Constructs an empty {@code PersonFilter} that shows all people by default.
     */
    public PersonFilter() {
        this.nameFilters = new LinkedHashSet<>();
        composeFilters();
    }

    /**
     * Constructs a {@code PersonFilter} from lists of filters.
     * Every field must be present and not null.
     */
    public PersonFilter(Set<Predicate<Name>> nameFilters) {
        requireAllNonNull(nameFilters);
        this.nameFilters = nameFilters;

        composeFilters();
    }

    /**
     * Checks if person filter contains any of the filters from another person filter.
     */
    public boolean has(PersonFilter personFilter) {
        return !Collections.disjoint(this.nameFilters, personFilter.nameFilters);
    }

    /**
     * Add all filters from another person filter and merge them.
     */
    public PersonFilter add(PersonFilter personFilter) {
        nameFilters.addAll(personFilter.nameFilters);

        composeFilters();
        return this;
    }

    /**
     * Remove filters from this person filter according to another person filter.
     */
    public PersonFilter remove(PersonFilter personFilter) {
        nameFilters.removeAll(personFilter.nameFilters);

        composeFilters();
        return this;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonFilter)) {
            return false;
        }

        PersonFilter otherPersonFilter = (PersonFilter) other;
        return otherPersonFilter.nameFilters.equals(nameFilters);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(nameFilters);
    }

    @Override
    public boolean test(Person person) {
        if (person == null) {
            return false;
        }

        boolean isFiltered = false;
        isFiltered = isFiltered || composedNameFilter.test(person.getName());
        return isFiltered;
    }

    private void composeFilters() {
        this.composedNameFilter = nameFilters.stream()
                .reduce((x, y) -> x.or(y))
                .orElse(x -> true);
    }
}