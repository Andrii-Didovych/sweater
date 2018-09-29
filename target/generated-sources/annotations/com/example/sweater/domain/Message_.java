package com.example.sweater.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, String> filename;
	public static volatile SingularAttribute<Message, User> author;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SingularAttribute<Message, String> text;
	public static volatile SingularAttribute<Message, String> tag;

}

