package com.example.ladi.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QConfig is a Querydsl query type for Config
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QConfig extends EntityPathBase<Config> {

    private static final long serialVersionUID = 635736266L;

    public static final QConfig config = new QConfig("config");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath code = createString("code");

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final StringPath name = createString("name");

    public final StringPath note = createString("note");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath value = createString("value");

    public QConfig(String variable) {
        super(Config.class, forVariable(variable));
    }

    public QConfig(Path<? extends Config> path) {
        super(path.getType(), path.getMetadata());
    }

    public QConfig(PathMetadata metadata) {
        super(Config.class, metadata);
    }

}

