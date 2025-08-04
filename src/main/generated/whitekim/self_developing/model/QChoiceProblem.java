package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import whitekim.self_developing.model.type.ProblemType;


/**
 * QChoiceProblem is a Querydsl query type for ChoiceProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChoiceProblem extends EntityPathBase<ChoiceProblem> {

    private static final long serialVersionUID = -1409620997L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QChoiceProblem choiceProblem = new QChoiceProblem("choiceProblem");

    public final QProblem _super;

    public final ListPath<String, StringPath> answer = this.<String, StringPath>createList("answer", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified;

    // inherited
    public final QCertification certification;

    //inherited
    public final StringPath comment;

    //inherited
    public final NumberPath<Long> id;

    // inherited
    public final QImage image;

    // inherited
    public final QPaper paper;

    //inherited
    public final StringPath problem;

    //inherited
    public final StringPath round;

    //inherited
    public final NumberPath<Integer> score;

    //inherited
    public final StringPath subject;

    public final ListPath<String, StringPath> suggest = this.<String, StringPath>createList("suggest", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final StringPath title;

    public final EnumPath<ProblemType> type = createEnum("type", ProblemType.class);

    public QChoiceProblem(String variable) {
        this(ChoiceProblem.class, forVariable(variable), INITS);
    }

    public QChoiceProblem(Path<? extends ChoiceProblem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QChoiceProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QChoiceProblem(PathMetadata metadata, PathInits inits) {
        this(ChoiceProblem.class, metadata, inits);
    }

    public QChoiceProblem(Class<? extends ChoiceProblem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QProblem(type, metadata, inits);
        this.atCreated = _super.atCreated;
        this.atModified = _super.atModified;
        this.certification = _super.certification;
        this.comment = _super.comment;
        this.id = _super.id;
        this.image = _super.image;
        this.paper = _super.paper;
        this.problem = _super.problem;
        this.round = _super.round;
        this.score = _super.score;
        this.subject = _super.subject;
        this.title = _super.title;
    }

}

