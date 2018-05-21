package com.dataextraction.security.oauth2.service;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dataextraction.model.User;
import com.dataextraction.persistence.MongoFactory;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	MongoFactory mongoFactory;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		MongoCollection<Document> col = mongoFactory.getMongoDB().getCollection("users");

		FindIterable<Document> it = col.find(Filters.eq("userName", userName));

		MongoCursor<Document> cursor = it.iterator();

		Document doc = null;
		User user = null;
		while (cursor.hasNext()) {

			doc = cursor.next();
			// user = copyDocToUser(doc);
			return org.springframework.security.core.userdetails.User.
			// .withDefaultPasswordEncoder()
					withUsername((String) doc.get("userName"))
					.password("$2a$08$qvrzQZ7jJ7oy2p/msL4M0.l83Cd0jNsX6AJUitbgRXGzge4j035ha") // "admin1234"
					.authorities("ROLE_USER", "ROLE_USER_INSERT")
					// .roles("USER")
					.build();
		}

		// if (user != null) {
		// return new
		// org.springframework.security.core.userdetails.User(user.getUserName(),
		// user.getPassword(), true,
		// true, true, true, AuthorityUtils.createAuthorityList("ROLE_USER", "write"));
		// }

		throw new UsernameNotFoundException(userName);
	}

}
