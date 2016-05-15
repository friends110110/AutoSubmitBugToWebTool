package com.example.tests.bean;
@Deprecated
public class FieldBean {

	String problemId;
	String solution;
	String comment;
	public FieldBean() {
		super();
	}
	public FieldBean(String problemId, String solution, String comment) {
		super();
		this.problemId = problemId;
		this.solution = solution;
		this.comment = comment;
	}
	public String getProblemId() {
		return problemId;
	}
	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	


}
