package com.example.ladi.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWork is a Querydsl query type for Work
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWork extends EntityPathBase<Work> {

    private static final long serialVersionUID = -1840081255L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWork work = new QWork("work");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAccount account;

    public final NumberPath<Integer> donGiao = createNumber("donGiao", Integer.class);

    public final NumberPath<Integer> donHoanThanh = createNumber("donHoanThanh", Integer.class);

    public final StringPath ghiChu = createString("ghiChu");

    //inherited
    public final NumberPath<Integer> id = _super.id;

    public final NumberPath<Integer> isActive = createNumber("isActive", Integer.class);

    public final NumberPath<Long> timeIn = createNumber("timeIn", Long.class);

    public final NumberPath<Long> timeOut = createNumber("timeOut", Long.class);

    public QWork(String variable) {
        this(Work.class, forVariable(variable), INITS);
    }

    public QWork(Path<? extends Work> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWork(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWork(PathMetadata metadata, PathInits inits) {
        this(Work.class, metadata, inits);
    }

    public QWork(Class<? extends Work> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
    }

}

