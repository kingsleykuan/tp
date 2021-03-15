package seedu.address.model.appointment;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.Appointment;
import seedu.address.model.person.Email;

import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 * Since the {@code Name} is tagged to the Person object, the Person object needs to be retrieve first.
 * Then, {@code Email} will be extracted out to perform the Predicate Search.
 */
public class EmailPredicate implements Predicate<Appointment> {
    private final List<Email> emails;

    public EmailPredicate(List<Email> emails) {
        this.emails = emails;
    }

    @Override
    public boolean test(Appointment appointment) {
        return emails.stream().anyMatch(email -> {
            System.out.println(email.value);
            return email.equals(appointment.getEmail());
        });
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmailPredicate // instanceof handles nulls
                && emails.equals(((EmailPredicate) other).emails)); // state check
    }
}
