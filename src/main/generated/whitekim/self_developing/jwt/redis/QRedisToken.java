package whitekim.self_developing.jwt.redis;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRedisToken is a Querydsl query type for RedisToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRedisToken extends EntityPathBase<RedisToken> {

    private static final long serialVersionUID = -19433002L;

    public static final QRedisToken redisToken = new QRedisToken("redisToken");

    public final StringPath accessToken = createString("accessToken");

    public final StringPath id = createString("id");

    public final StringPath refreshToken = createString("refreshToken");

    public QRedisToken(String variable) {
        super(RedisToken.class, forVariable(variable));
    }

    public QRedisToken(Path<? extends RedisToken> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRedisToken(PathMetadata metadata) {
        super(RedisToken.class, metadata);
    }

}

