package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCertification is a Querydsl query type for Certification
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCertification extends EntityPathBase<Certification> {

    private static final long serialVersionUID = -1000496393L;

    public static final QCertification certification = new QCertification("certification");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated = _super.atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified = _super.atModified;

    public final StringPath certName = createString("certName");

    public final NumberPath<Long> examTime = createNumber("examTime", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Long> subjectCount = createNumber("subjectCount", Long.class);

    public QCertification(String variable) {
        super(Certification.class, forVariable(variable));
    }

    public QCertification(Path<? extends Certification> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCertification(PathMetadata metadata) {
        super(Certification.class, metadata);
    }

}

