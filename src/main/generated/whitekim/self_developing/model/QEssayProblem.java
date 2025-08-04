package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QEssayProblem is a Querydsl query type for EssayProblem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEssayProblem extends EntityPathBase<EssayProblem> {

    private static final long serialVersionUID = 385380741L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEssayProblem essayProblem = new QEssayProblem("essayProblem");

    public final QProblem _super;

    public final StringPath answer = createString("answer");

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

    //inherited
    public final StringPath title;

    public final EnumPath<ProblemType> type = createEnum("type", ProblemType.class);

    public QEssayProblem(String variable) {
        this(EssayProblem.class, forVariable(variable), INITS);
    }

    public QEssayProblem(Path<? extends EssayProblem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEssayProblem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEssayProblem(PathMetadata metadata, PathInits inits) {
        this(EssayProblem.class, metadata, inits);
    }

    public QEssayProblem(Class<? extends EssayProblem> type, PathMetadata metadata, PathInits inits) {
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

