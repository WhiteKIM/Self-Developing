package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProblem is a Querydsl query type for Problem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProblem extends EntityPathBase<Problem> {

    private static final long serialVersionUID = -280509796L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProblem problem1 = new QProblem("problem1");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated = _super.atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified = _super.atModified;

    public final QCertification certification;

    public final StringPath comment = createString("comment");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QImage image;

    public final QPaper paper;

    public final StringPath problem = createString("problem");

    public final StringPath round = createString("round");

    public final NumberPath<Integer> score = createNumber("score", Integer.class);

    public final StringPath subject = createString("subject");

    public final StringPath title = createString("title");

    public QProblem(String variable) {
        this(Problem.class, forVariable(variable), INITS);
    }

    public QProblem(Path<? extends Problem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProblem(PathMetadata metadata, PathInits inits) {
        this(Problem.class, metadata, inits);
    }

    public QProblem(Class<? extends Problem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.certification = inits.isInitialized("certification") ? new QCertification(forProperty("certification")) : null;
        this.image = inits.isInitialized("image") ? new QImage(forProperty("image")) : null;
        this.paper = inits.isInitialized("paper") ? new QPaper(forProperty("paper"), inits.get("paper")) : null;
    }

}

