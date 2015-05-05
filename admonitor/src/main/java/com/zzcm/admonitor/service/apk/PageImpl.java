package com.zzcm.admonitor.service.apk;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 分页工具类
 * @author qiulongjie
 *
 * @param <T>
 */
public class PageImpl<T> implements Page<T>,Serializable {

	private static final long serialVersionUID = 1L;

	private final List<T> content;
	private final long total;
	private int pageNumber;
	private int pageSize;
	
	public PageImpl(List<T> content , long total , int pageNumber , int pageSize) {
		if (null == content) {
			throw new IllegalArgumentException("Content must not be null!");
		}
		this.content = content;
		this.total = total;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	/**
	 * 获取当前页
	 */
	public int getNumber() {
		return pageNumber;
	}

	/**
	 * 每页大小
	 */
	public int getSize() {
		return pageSize;
	}

	/**
	 * 总共多少页
	 */
	public int getTotalPages() {
		return  getSize() == 0 ? 1 : (int) Math.ceil((double) total / (double) getSize());
	}

	/**
	 * 当页数据大小
	 */
	public int getNumberOfElements() {
		return content.size();
	}

	/**
	 * 总数据大小
	 */
	public long getTotalElements() {
		return total;
	}

	/**
	 * 是否有前一页
	 */
	public boolean hasPreviousPage() {
		return getNumber() > 0;
	}

	/**
	 * 是否是第一页
	 */
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}

	/**
	 * 是否有下一页
	 */
	public boolean hasNextPage() {
		return getNumber() + 1 < getTotalPages();
	}

	/**
	 * 是否是最后一页
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	@Override
	public Pageable nextPageable() {
		return null;
	}

	@Override
	public Pageable previousPageable() {
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return content.iterator();
	}

	@Override
	public List<T> getContent() {
		return Collections.unmodifiableList(content);
	}

	@Override
	public boolean hasContent() {
		return !content.isEmpty();
	}

	@Override
	public Sort getSort() {
		return null;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PageImpl<?>)) {
			return false;
		}

		PageImpl<?> that = (PageImpl<?>) obj;

		boolean totalEqual = this.total == that.total;
		boolean contentEqual = this.content.equals(that.content);

		return totalEqual && contentEqual;
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + (int) (total ^ total >>> 32);
		result = 31 * result + content.hashCode();

		return result;
	}
}
