package com.example.sweater.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, User> subscribers;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Boolean> active;
	public static volatile SingularAttribute<User, String> photo;
	public static volatile SetAttribute<User, Message> messages;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> activationCode;
	public static volatile SetAttribute<User, User> subscription;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;

}

