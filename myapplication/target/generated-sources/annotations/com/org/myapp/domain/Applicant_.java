package com.org.myapp.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Applicant.class)
public abstract class Applicant_ {

	public static volatile SingularAttribute<Applicant, byte[]> resume;
	public static volatile SingularAttribute<Applicant, String> resumeContentType;
	public static volatile SingularAttribute<Applicant, String> phone;
	public static volatile SingularAttribute<Applicant, String> name;
	public static volatile SingularAttribute<Applicant, Long> id;
	public static volatile SingularAttribute<Applicant, Job> job;
	public static volatile SingularAttribute<Applicant, String> email;

}

