package category.social.subs;

import category.social.SocialNetworkService;

public class Facebook extends SocialNetworkService { //SocialNetworkService Class�� ��ӹ���(is a)
	public static int serviceCnt = 1;	//���� ī��Ʈ. static
	private int userNo;	//������ serviceCnt ��
	private int postCnt;	//������ post ����
	private String lastPostContents;	//���������� �ۼ��� post�� ����
	
	//Constructor Overloading
	public Facebook(String userEmail, String userPasswd, String userName) {
		this(userEmail, userPasswd, userName, null, null);
	}

	public Facebook(String userEmail, String userPasswd, String userName, String userPhone, String userAddress) {
		super("Facebook", userEmail, userPasswd, userName, userPhone, userAddress);
		this.userNo = serviceCnt++;
		this.postCnt = 0;
		this.lastPostContents = "** Empty Contents **";
	}
	
	public int getUserNo() { return userNo; }
	public void setUserNo(int userNo) { this.userNo = userNo; }
	public int getPostCnt() { return postCnt; }
	public void setPostCnt(int postCnt) { this.postCnt = postCnt; }
	public String getLastPostContents() { return lastPostContents; }
	public void setLastPostContents(String lastPostContents) { this.lastPostContents = lastPostContents; }
	
	/* �α��� Ȯ�� ���� �޼��� */
	private boolean isLogin() {
		if(super.serviceLogin == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/* ������ �޼���(���� ����) */
	public void writePost(String Contents) {
		if(isLogin() == true) {	//�α��� ���� Ȯ��
			this.lastPostContents = Contents;
			this.postCnt++;
			System.out.println("Facebook Posting Success");
			return;
		}
		else {
			System.out.println("Posting Failed : Facebook Not Login");
			return;
		}
	}
	/* ������ �޼���(���� ����) */
	//Method Overloading
	public void writePost() {
		if(isLogin() == true) {
			this.lastPostContents = "** Empty Contents **";
			this.postCnt++;
			System.out.println("Facebook Posting Success");
			return;
		}
		else {
			System.out.println("Posting Failed : Facebook Not Login");
			return;
		}
	}
	
	/* ��ü ���� ǥ�� */
	public void showFacebookInfo() {
		super.showServiceInfo();
		System.out.println("Facebook User Number : " + this.userNo);
		System.out.println("Facebook Total Post Count : " + this.postCnt);
		System.out.println("Facebook Last Post Contents : " + this.lastPostContents);
		return;
	}
}
