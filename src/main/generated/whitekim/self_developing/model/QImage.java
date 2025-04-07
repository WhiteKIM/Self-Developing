package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QImage is a Querydsl query type for Image
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QImage extends EntityPathBase<Image> {

    private static final long serialVersionUID = -713063272L;

    public static final QImage image = new QImage("image");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated = _super.atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified = _super.atModified;

    public final StringPath filename = createString("filename");

    public final StringPath filepath = createString("filepath");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath originalFilename = createString("originalFilename");

    public QImage(String variable) {
        super(Image.class, forVariable(variable));
    }

    public QImage(Path<? extends Image> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImage(PathMetadata metadata) {
        super(Image.class, metadata);
    }

}

