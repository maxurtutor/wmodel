package org.maxur.wmodel.domain;

import java.util.concurrent.atomic.AtomicInteger;

import static org.maxur.wmodel.domain.ServiceLocatorProvider.service;

/**
 * @author myunusov
 * @version 1.0
 * @since <pre>04.11.2015</pre>
 */
public abstract class Group extends Entity {

    protected Group(String id) {
        super(id);
    }

    public static Group make(String id, String name, int userNumber) {
        return new GroupImpl(id, name, userNumber);
    }

    public abstract String getName();

    public abstract void addUser(User user) throws ValidationException;

    private static class GroupImpl extends Group {

        public static final int MAX_CAPACITY = 5;

        private final String name;

        private final AtomicInteger userNumber;

        public GroupImpl(String id, String name, int userNumber) {
            super(id);
            this.name = name;
            this.userNumber = new AtomicInteger(userNumber);
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public void addUser(User user) throws ValidationException {
            if (isCompleted()) {
                throw new ValidationException("More users than allowed in group");
            }
            user.setGroup(this);
            final UserRepository repository = service(UserRepository.class);
            repository.insert(user);
            userNumber.incrementAndGet();
        }

        private boolean isCompleted() {
            return userNumber.get() == MAX_CAPACITY;
        }
    }
}
