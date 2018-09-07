package com.ao.scanElectricityBis.base;

public interface UserInfo {
	/**
	 * 
	 * @return
	 */
	public String getName();
	/**
	 * ȡ�õ�ǰ�û�ID
	 * @return
	 */
	public int getUser();
	/**
	 * �û�ͷ��
	 * @return
	 */
	public String getAvatar();
	/**
	 * �Ƿ�Ϊ����Ա�û�
	 * @return
	 */
	public boolean isAdminUser();
	
	
	/**
	 * ȡ�ù���Ȩ������
	 * @return
	 */
	public String getRightAreaCode();
	
	/**
	 * ȡ���û���Ӧ����Ӫ��ID
	 * @return
	 */
	public Integer getOperatorId();
	
	/**
	 * �Ƿ�ӵ�ж�ӦȨ����
	 * @param rightKey	 
	 * @return
	 */
	public boolean isHasRight(int rightKey);
	

}

