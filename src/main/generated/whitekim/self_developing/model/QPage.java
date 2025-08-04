package whitekim.self_developing.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import whitekim.self_developing.model.type.PageType;


/**
 * QPage is a Querydsl query type for Page
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPage extends EntityPathBase<Page> {

    private static final long serialVersionUID = 1362668466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPage page = new QPage("page");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atCreated = _super.atCreated;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> atModified = _super.atModified;

    public final QCategory category;

    public final QCertification certification;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final EnumPath<PageType> pageType = createEnum("pageType", PageType.class);

    public final ListPath<Paper, QPaper> paperList = this.<Paper, QPaper>createList("paperList", Paper.class, QPaper.class, PathInits.DIRECT2);

    public QPage(String variable) {
        this(Page.class, forVariable(variable), INITS);
    }

    public QPage(Path<? extends Page> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPage(PathMetadata metadata, PathInits inits) {
        this(Page.class, metadata, inits);
    }

    public QPage(Class<? extends Page> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.certification = inits.isInitialized("certification") ? new QCertification(forProperty("certification")) : null;
    }

}

