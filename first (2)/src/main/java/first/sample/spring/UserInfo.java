package first.sample.spring;

public class UserInfo {
	
	String uid;
	int idx;
	String unick;
	boolean error;
	
	public UserInfo(boolean error) {
		this.error = error;
	}
	
	public UserInfo(String id, int idx, String nickname) {
		this.uid = id;
		this.idx = idx;
		this.unick = nickname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUnick() {
		return unick;
	}

	public void setUnick(String unick) {
		this.unick = unick;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}
	
	
}
