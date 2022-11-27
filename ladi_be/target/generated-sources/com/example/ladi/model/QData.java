package com.example.ladi.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QData is a Querydsl query type for Data
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QData extends EntityPathBase<Data> {

    private static final long serialVersionUID = -1840660686L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QData data = new QData("data");

    public final QAccount account;

    public final StringPath country = createString("country");

    public final NumberPath<Long> date = createNumber("date", Long.class);

    public final StringPath dateChanged = createString("dateChanged");

    public final StringPath district = createString("district");

    public final StringPath formcolor = createString("formcolor");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath ipAddress = createString("ipAddress");

    public final StringPath name = createString("name");

    public final StringPath phone = createString("phone");

    public final NumberPath<Double> price = createNumber("price", Double.class);

    public final StringPath source = createString("source");

    public final StringPath state = createString("state");

    public final NumberPath<Integer> status = createNumber("status", Integer.class);

    public final StringPath street = createString("street");

    public final StringPath utmCampation = createString("utmCampation");

    public final StringPath utmContent = createString("utmContent");

    public final StringPath utmMedium = createString("utmMedium");

    public final StringPath utmSource = createString("utmSource");

    public final StringPath utmTerm = createString("utmTerm");

    public final StringPath variantUrl = createString("variantUrl");

    public final StringPath ward = createString("ward");

    public QData(String variable) {
        this(Data.class, forVariable(variable), INITS);
    }

    public QData(Path<? extends Data> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QData(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QData(PathMetadata metadata, PathInits inits) {
        this(Data.class, metadata, inits);
    }

    public QData(Class<? extends Data> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new QAccount(forProperty("account")) : null;
    }

}

