package test09.integrate_hibernate_jpa;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Id生成器
 * @author zhangqingli
 *
 */
@MappedSuperclass
public abstract class IdEntity {
	protected String id;

	/**
	 * @see org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory
	 * 
	 */
	@Id
	@GeneratedValue(generator="uuidGenerator")
	@GenericGenerator(name="uuidGenerator", strategy="uuid.hex")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
