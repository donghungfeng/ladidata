package com.example.ladi.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccount is a Querydsl query type for Account
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccount extends EntityPathBase<Account> {

    private static final long serialVersionUID = 399519141L;

    public static final QAccount account = new QAccount("account");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Integer> active = createNumber("active", Integer.class);

    public final StringPath address = createString("address");

    public final StringPath email = createString("email");

    public final StringPath fullName = createString("fullName");

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath note = createString("note");

    public final StringPath passWord = createString("passWord");

    public final StringPath phone = createString("phone");

    public final StringPath role = createString("role");

    public final StringPath userName = createString("userName");

    public QAccount(String variable) {
        super(Account.class, forVariable(variable));
    }

    public QAccount(Path<? extends Account> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccount(PathMetadata metadata) {
        super(Account.class, metadata);
    }

}

