package kr.nexg.esm.common;

public interface EvConstants {
	/** 임시토큰 발행 */
	String TOKEN_URL = "/auth/oauth/token";
	
	/** 이메일 중복 체크 */
	String IS_EMAIL_DUPLICATED_URL = "/auth/isEmailDuplicated";
	
	/** 이메일 인증 */
	String SEND_VERIFICATION_EMAIL_URL = "/api/m-service-mobile/user/sendVerificationEmail";
	
	/** 회원가입 */
	String SIGNUP_USER_URL = "/auth/signupUser";
	
	/** 공지사항 조회 */
	String GET_NOITS_URL = "/api/m-service-mobile/community/getNotis";
	
	/** 자유게시판 조회 */
	String GET_FREE_POSTS_URL = "/api/m-service-mobile/community/getFreePosts";
	
	/** 충전소게시판 조회 */
	String GET_RECHGST_POSTS_URL = "/api/m-service-mobile/community/getRechgstPosts";
	
	/** 댓글 조회 */
	String GET_COMMENTS_URL = "/api/m-service-mobile/community/getComments";
	
	/** 자주하는 질문 (FAQ) */
	String GET_FAQS_URL = "/api/m-service-mobile/meta/getFaqs";
	
	/** 문의하기 */
	String INQUIRY_URL = "/api/m-service-mobile/question/insert";
	
	/** 문의하기 답변 조회 */
	String GET_USER_QUESTIONS_URL = "/api/m-service-mobile/question/getUserQuestions";
	
	/** 차량 충전 이력 */
	String GET_USER_RECHG_INFO_URL = "/api/m-service-mobile/rechgst/getUserRechgInfo";
	
	/** 충전소 목록 */
	String GET_ELINK_RECHGSTS_URL = "/api/m-service-mobile/rechgst/getELinkRechgsts";
	
	/** Image 가져오기 */
	String ATCH_IMAGE_URL = "/api/m-service-mobile/atch/image";
	
	/** 충전기 정보 */
	String GET_RECHGST_RECHGR_URL = "/api/m-service-mobile/rechgst/getRechgstRechgr";

}
