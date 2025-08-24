package whitekim.self_developing.model.enumerate;

import whitekim.self_developing.exception.NotFoundDataException;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author whitekim
 * 추천 비추천 반응에 대한 타입 작성
 */
public enum Reaction {
    POSITIVE("Like"),   // 추천
    NEGATIVE("DisLike");    // 비추천

    private static final Map<String, Reaction> actionMap =
            Stream.of(values()).collect(Collectors.toMap(Reaction::getValue, reaction -> reaction));
    private final String value;

    Reaction(String value) {
        this.value = value;
    }

    public static Reaction fromValue(String value) {
        Reaction reaction = actionMap.get(value);

        if(reaction == null) {
            throw new NotFoundDataException("[Reaction] 존재하지 않는 타입입니다.");
        }

        return reaction;
    }

    public String getValue() {
        return value;
    }
 }
