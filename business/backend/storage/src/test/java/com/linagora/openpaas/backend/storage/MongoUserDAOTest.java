package com.linagora.openpaas.backend.storage;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.verify;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.linagora.openpaas.backend.storage.User.Id;


public class MongoUserDAOTest {

	private MongoTestServer mongoTestServer;
	private MongoUserDAO userDao;
    
    @Before
    public void setUp() throws Exception {
        mongoTestServer = new MongoTestServer();
        mongoTestServer.start();
        userDao = new MongoUserDAO(mongoTestServer.client().getDB("testDB"));
    }

    @After
    public void tearDown() throws Exception {
    	mongoTestServer.stop();
    }

    @Test
    public void userCreation() {
    	User userToCreate = User.builder().login("myuser").build();
		User actualUser = userDao.create(userToCreate);
		assertThat(actualUser.getLogin()).isEqualTo("myuser");
		assertThat(actualUser.getId()).isNotNull();
    }

    @Test(expected=DuplicateLoginException.class)
    public void duplicateUserCreation() {
    	User userOne = User.builder().login("myUser").build();
    	User userTwo = User.builder().login("myUser").build();
		userDao.create(userOne);
		userDao.create(userTwo);
    }
    
    @Test
    public void listSomeUsers() {
    	User userOne = User.builder().login("userOne").build();
    	User userTwo = User.builder().login("userTwo").build();
    	User userThree = User.builder().login("userThree").build();
    	userDao.create(userOne);
    	userDao.create(userTwo);
    	userDao.create(userThree);
    	
		Iterable<User> users = userDao.findAll();

		//fest doesn't support Iterables well
		assertThat(ImmutableList.copyOf(users)).containsOnly(userOne, userTwo, userThree);
    }

    @Test
    public void listNoUsers() {
		Iterable<User> users = userDao.findAll();

		assertThat(users).isEmpty();
    }
 
    @Test
    public void getUser() {
    	User userOne = User.builder().login("userOne").build();
    	User userTwo = User.builder().login("userTwo").build();
    	User userThree = User.builder().login("userThree").build();
    	userDao.create(userOne);
    	userDao.create(userTwo);
    	userDao.create(userThree);
    	
		User actual = userDao.getUser(userOne.getId());

		assertThat(actual).isEqualsToByComparingFields(userOne);
    }

    @Test
    public void getUnknownUser() {
    	User userOne = User.builder().login("userOne").build();
    	userDao.create(userOne);
    	
    	Id id = createMock(User.Id.class);
    	expect(id.getId()).andReturn(ObjectId.get());
    	replay(id);
    	
		User actual = userDao.getUser(id);
		verify(id);
		
		assertThat(actual).isNull();
    }
    
}
