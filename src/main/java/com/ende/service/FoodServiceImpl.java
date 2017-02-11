package com.ende.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ende.domain.MeatFood;
import com.ende.domain.MilkFood;
import com.ende.form.MeatFoodCriteria;
import com.ende.form.MilkFoodCriteria;
import com.ende.repository.MeatFoodRepository;
import com.ende.repository.MilkFoodRepository;

@Component("foodService")
@Transactional
public class FoodServiceImpl implements FoodService {
	
	private final MeatFoodRepository meatFoodRepository;
	private final MilkFoodRepository milkFoodRepository;

	public FoodServiceImpl( MeatFoodRepository meatFoodRepository, MilkFoodRepository milkFoodRepository){
		this.meatFoodRepository = meatFoodRepository;
		this.milkFoodRepository = milkFoodRepository;
	}
	
	public MeatFood save(MeatFood mf) {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		if(null == mf.getId())
			mf.setCreatetime(t);
		mf.setUpdatetime(t);
		return this.meatFoodRepository.save(mf);
	}
	@Override
	public Page<MilkFood> searchMilkFood(MilkFoodCriteria mfc, Pageable page) {
		return this.milkFoodRepository.findAll(new Specification<MilkFood>(){
			@Override
			public Predicate toPredicate(Root<MilkFood> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				cq = cb.createQuery(MilkFood.class);
				Predicate p1 = null;
				Predicate p2 = null;
				Predicate p3 = null;
				Predicate p4 = null;
				
				if(StringUtils.hasLength(mfc.getType())){
					int[] a = convert(mfc.getType());
					for(int i=0; i<a.length; i++) {
						if(null == p1)
							p1 = cb.equal(root.get("type").as(Integer.class), a[i]);
						else
							p1 = cb.or(p1,cb.equal(root.get("type").as(Integer.class), a[i]));
					}
				}else
					p1 = cb.conjunction();
				
				if(StringUtils.hasLength(mfc.getSourcetype())){
					int[] a = convert(mfc.getSourcetype());
					for(int i=0; i<a.length; i++) {
						if(null == p2)
							p2 = cb.equal(root.get("sourcetype").as(Integer.class), a[i]);
						else
							p2 = cb.or(p2,cb.equal(root.get("sourcetype").as(Integer.class), a[i]));
					}
				}else
					p2 = cb.conjunction();
				
				if(StringUtils.hasLength(mfc.getOther())){
					Predicate op1 = cb.like(root.get("contactor"), "%" + mfc.getOther() + "%");
					Predicate op2 = cb.like(root.get("address"), "%" + mfc.getOther() + "%");
					Predicate op3 = cb.like(root.get("note"), "%" + mfc.getOther() + "%");
					p3 = cb.or(op1,op2,op3);					
				}
				else
					p3 = cb.conjunction();
				
				//if(mfc.getMinprice()!= mfc.getMaxprice()){
					p4 = cb.between(root.get("price"), mfc.getMinprice(), mfc.getMaxprice());
				/*}
				else
					p4 = cb.conjunction();*/
				
				cq.where(p1, p2, p3, p4);
				return cq.getRestriction();
			}
			
		}, new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(Direction.DESC,"createtime")));
	}
	
	@Override
	public Page<MeatFood> searchMeatFood(MeatFoodCriteria mfc, Pageable page) {
		
		return this.meatFoodRepository.findAll(new Specification<MeatFood>(){
			@Override
			public Predicate toPredicate(Root<MeatFood> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				cq = cb.createQuery(MeatFood.class);
				Predicate p1 = null;
				Predicate p2 = null;
				Predicate p3 = null;
				Predicate p4 = null;
				Predicate p5 = null;
				if(StringUtils.hasLength(mfc.getSpecies())){
					int[] a = convert(mfc.getSpecies());
					for(int i=0; i<a.length; i++) {
						if(null == p1)
							p1 = cb.equal(root.get("species").as(Integer.class), a[i]);
						else
							p1 = cb.or(p1,cb.equal(root.get("species").as(Integer.class), a[i]));
					}
				}else
					p1 = cb.conjunction();
				
				if(StringUtils.hasLength(mfc.getBreed())){
					int[] a = convert(mfc.getBreed());
					for(int i=0; i<a.length; i++) {
						if(null == p2)
							p2 = cb.equal(root.get("breed").as(Integer.class), a[i]);
						else
							p2 = cb.or(p2,cb.equal(root.get("breed").as(Integer.class), a[i]));
					}
				}else
					p2 = cb.conjunction();
				if(StringUtils.hasLength(mfc.getType())){
					int[] a = convert(mfc.getType());
					for(int i=0; i<a.length; i++) {
						if( null == p3)
							p3 = cb.equal(root.get("type").as(Integer.class), a[i]);
						else
							p3 = cb.or(p3,cb.equal(root.get("type").as(Integer.class), a[i]));
					}
				}else
					p3 = cb.conjunction();
				
				if(StringUtils.hasLength(mfc.getFeedtype())){
					int[] a = convert(mfc.getFeedtype());
					for(int i=0; i<a.length; i++) {
						if(null == p4)
							p4 = cb.equal(root.get("feedtype").as(Integer.class), a[i]);
						else
							p4 = cb.or(p4,cb.equal(root.get("feedtype").as(Integer.class), a[i]));
					}
				}else
					p4 = cb.conjunction();
				
				if(StringUtils.hasLength(mfc.getOther())){
					Predicate op1 = cb.like(root.get("contactor"), "%" + mfc.getOther() + "%");
					Predicate op2 = cb.like(root.get("address"), "%" + mfc.getOther() + "%");
					Predicate op3 = cb.like(root.get("note"), "%" + mfc.getOther() + "%");
					p5 = cb.or(op1,op2,op3);					
				}
				else
					p5 = cb.conjunction();
				cq.where(p1, p2, p3, p4, p5);
				return cq.getRestriction();
			}
			
		}, new PageRequest(page.getPageNumber(), page.getPageSize(), new Sort(Direction.DESC,"createtime")));
	}

	@Override
	public MilkFood save(MilkFood mf) {
		Timestamp t = new Timestamp(System.currentTimeMillis());
		if(null == mf.getId())
			mf.setCreatetime(t);
		mf.setUpdatetime(t);
		return this.milkFoodRepository.save(mf);
	}

	@Override
	public Page<MeatFood> findMeatFoodByAccountId(Long id, Pageable pageable) {
		return this.meatFoodRepository.findByAccountid(id, pageable) ;
	}

	@Override
	public Page<MilkFood> findMilkFoodByAccountId(Long id, Pageable pageable) {
		return this.milkFoodRepository.findByAccountid(id, pageable) ;
	}

	@Override
	public boolean deleteMeat(Long id) {
		try{
			this.meatFoodRepository.delete(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deleteMilk(Long id) {
		try{
			this.milkFoodRepository.delete(id);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	private int[] convert(String str){
		String [] a = str.split(",");
		int[] b = new int[a.length];
		for(int i=0; i< a.length; i++ ){
			b[i] = Integer.valueOf(a[i]);
		}
		return b;
	}

	
	 
}
