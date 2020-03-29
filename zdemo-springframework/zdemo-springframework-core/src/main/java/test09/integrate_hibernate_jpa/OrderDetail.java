package test09.integrate_hibernate_jpa;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 订单明细表
 * 
 * @author zhangqingli
 *
 */
@Entity(name="t_order_detail")
@Table(name="t_order_detail")
public class OrderDetail {
	private Integer id;
	private Order order;
	private Item item; // 关联商品

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="order_id")
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="item_id", unique=true)
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

}
