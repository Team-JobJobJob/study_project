package team01.studyCm.user.entity.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Job {
    IT("IT/인터넷 전체"),
    WEBDEV("웹개발"),
    APPDEV("응용프로그램개발"),
    ERP("ERP/시스템개발/설계"),
    NETWORK("네트워크/서버/보안"),
    DBA("DBA/데이터베이스"),
    SITE("콘텐츠/사이트운영"),
    WEBMANAGE("웹기획/PM/웹마케팅"),
    FRONTEND("HTML/퍼블리싱/UI개발"),
    QA("QA/테스트/검증"),
    GAME("게임"),
    VIDEO("동영상제작/편집"),
    AI("빅데이터/AI");

    private final String key;
}
