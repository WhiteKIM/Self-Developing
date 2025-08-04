package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import whitekim.self_developing.model.type.PaperType;


/**
 * QPaper is a Querydsl query type for Paper
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPaper extends EntityPathBase<Paper> {

    private static final long serialVersionUID = -706941751L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPaper paper = new QPaper("paper");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated = _super.atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified = _super.atModified;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final QPage page;

    public final ListPath<Problem, QProblem> problemList = this.<Problem, QProblem>createList("problemList", Problem.class, QProblem.class, PathInits.DIRECT2);

    public final StringPath title = createString("title");

    public final EnumPath<PaperType> type = createEnum("type", PaperType.class);

    public QPaper(String variable) {
        this(Paper.class, forVariable(variable), INITS);
    }

    public QPaper(Path<? extends Paper> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPaper(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPaper(PathMetadata metadata, PathInits inits) {
        this(Paper.class, metadata, inits);
    }

    public QPaper(Class<? extends Paper> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.page = inits.isInitialized("page") ? new QPage(forProperty("page"), inits.get("page")) : null;
    }

}

