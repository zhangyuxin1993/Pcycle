package General;

public class CommonObject {
             String name;
             int index;
             String comments;
			public CommonObject(String name, int index, String comments) {
				// TODO Auto-generated constructor stub
            	this.name=name;
            	this.index=index;
            	this.comments=comments;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public int getIndex() {
				return index;
			}
			public void setIndex(int index) {
				this.index = index;
			}
			public String getComments() {
				return comments;
			}
			public void setComments(String comments) {
				this.comments = comments;
			}
             
}
