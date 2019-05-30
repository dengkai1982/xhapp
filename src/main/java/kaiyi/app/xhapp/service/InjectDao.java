package kaiyi.app.xhapp.service;

import kaiyi.puer.commons.bean.Identify;
import kaiyi.puer.commons.bean.Reflects;
import kaiyi.puer.commons.bean.field.DataValueProcessor;
import kaiyi.puer.commons.data.JavaDataTyper;
import kaiyi.puer.commons.data.StringEditor;
import kaiyi.puer.db.DataChangeNotify;
import kaiyi.puer.db.orm.*;
import kaiyi.puer.db.query.CompareQueryExpress;
import kaiyi.puer.db.query.NullQueryExpress;
import kaiyi.puer.db.query.QueryExpress;
import kaiyi.puer.h5ui.service.ApplicationService;
import kaiyi.puer.web.elements.FormElementHidden;
import kaiyi.puer.web.entity.LogicDeleteEntity;
import kaiyi.puer.web.service.EntityQueryService;
import kaiyi.puer.web.servlet.WebInteractive;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED)
public abstract class InjectDao<T> extends JpaDataOperImpl<T> implements DatabaseFastOper<T>,
		CustomQueryExpress,EntityQueryService,DataChangeNotify {
	private static final long serialVersionUID = -7804767008062125327L;
	@Resource
	protected ApplicationService applicationService;
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly = true)
	@Override
	public T findForPrimary(Serializable entityid) {
		return super.findForPrimary(entityid);
	}

	@PersistenceContext(unitName="pc_unit")
	@Override
	public void setEntityManager(EntityManager em) {
		this.em=em;
	}

	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void saveObject(T entity) throws ORMException {
		super.saveObject(entity);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void updateObject(T entity) throws ORMException {
		super.updateObject(entity);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public void deleteForPrimary(Serializable entityid) throws ORMException {
		super.deleteForPrimary(entityid);
	}
	@Transactional(propagation=Propagation.REQUIRED)
	@Override
	public int deleteByQuery(QueryExpress express) throws ORMException {
		return super.deleteByQuery(express);
	}

	/***
	 * 新增对象
	 * @param data
	 * @throws ORMException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public T  newObject(Map<String, JavaDataTyper> data)throws ServiceException{
		T t=Reflects.newObjectAndFieldBind(entityClass,data,getDataValueProcessor());
		objectBeforePersistHandler(t,data);
		saveObject(t);
		objectAfterPersistHandler(t,data);
		return t;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public T updateObject(Serializable primaryId, Map<String, JavaDataTyper> data)throws ServiceException{
		T t=findForPrimary(primaryId);
		data.remove(primaryId);
		objectBeforeUpdateHandler(t,data);
		Reflects.fieldValueOf(t,data,getDataValueProcessor());
		objectAfterUpdateHandler(t,data);
		return t;
	}

	/**
	 * 调用newObject持久化之前的回调方法
	 * @param t
	 */
	protected void objectBeforePersistHandler(T t,Map<String, JavaDataTyper> params)throws ServiceException{

	}
	/**
	 * 调用newObject持久化之后的回调方法
	 * @param t
	 */
	protected void objectAfterPersistHandler(T t,Map<String, JavaDataTyper> params)throws ServiceException{

	}
	protected  void objectBeforeUpdateHandler(T t,Map<String, JavaDataTyper> data)throws ServiceException{

	}
	/**
	 * 调用objectUpdate更新之后的回调方法
	 * @param t
	 */
	protected void objectAfterUpdateHandler(T t,Map<String, JavaDataTyper> params)throws ServiceException{

	}
	/**
	 * 自动新增、更新时，当传入参数是非基本对象时，需要提供转换方法
	 * @return
	 */
	protected DataValueProcessor getDataValueProcessor(){return null;};

	@Override
	public void registDatasourceStatusNotify(DataChangeNotify notifyer) {
		super.registDatasourceStatusNotify(notifyer);
	}
	/**
	 * 子类要重写，那么请首先调用父类的getCustomerQuery得到QueryExpress之后再LinkExpress
	 * @return
	 */
	@Override
	public QueryExpress getCustomerQuery(Map<String,JavaDataTyper> params) {
		if(entityClass.getName().equals(LogicDeleteEntity.class.getName())){
			return new CompareQueryExpress("enable",
					CompareQueryExpress.Compare.EQUAL,Boolean.TRUE);
		}else if(Reflects.fieldNameExist(entityClass,"entityId")){
			return new NullQueryExpress("entityId",NullQueryExpress.NullCondition.IS_NOT_NULL);
		}
		return null;
	}

	@Override
	public FormElementHidden[] gerenatorHiddenElement(WebInteractive webInteractive) {
		return null;
	}

	@Override
	public void beforePersist(Object entity) {

	}

	@Override
	public void afterPersist(Object entity) {

	}

	@Override
	public void beforeUpdate(Object entity) {

	}

	@Override
	public void afterUpdate(Object entity) {

	}

	@Override
	public void beforeDelete(Object entity)throws ORMException {
	}

	@Override
	public void afterDelete(Object entity) {

	}

	@Override
	public String getBeanName() {
		StringEditor editor=new StringEditor(getSimpleEntityClassName()+"Service");
		return editor.lowerFirst().getValue();
	}
	protected String[] getFormElementHiddenParams(){
		return new String[0];
	}
	protected boolean existParameter(Map<String, JavaDataTyper> params,String name){
		return params.get(name)!=null;

	}
}
