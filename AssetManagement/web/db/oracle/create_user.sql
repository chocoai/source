
create user jeesite
	identified by jeesite
	quota unlimited on users;
  
grant connect,resource,create session,select any table,
		create any view,create any table,create any index,
		drop any table,drop any view,drop any index
	to jeesite;
	
-- ������Դ�ֲ�ʽ�����£���Ҫ��Ŀ���û�����������Ȩ���������ʾ����ResourceException: Error in recovery
grant select on sys.dba_pending_transactions to jeesite;
grant select on sys.pending_trans$ to jeesite;
grant select on sys.dba_2pc_pending to jeesite;
grant execute on sys.dbms_system to jeesite;
