package com.shxy.w202350766.campusserviceplatform.pojo.vo;

import java.util.List;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 * 商品列表查询封装
 */
public class ProductPageResult {
    private Integer code;
    private String message;
    private PaginatedResponse data;

    public ProductPageResult() {
    }

    public ProductPageResult(Integer code, String message, PaginatedResponse data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ProductPageResult success(PaginatedResponse data) {
        return new ProductPageResult(200, "操作成功", data);
    }

    public static ProductPageResult success(List<?> list, Pagination pagination) {
        PaginatedResponse response = new PaginatedResponse(list, pagination);
        return new ProductPageResult(200, "操作成功", response);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PaginatedResponse getData() {
        return data;
    }

    public void setData(PaginatedResponse data) {
        this.data = data;
    }

    /**
     * 分页响应数据
     */
    public static class PaginatedResponse {
        private List<?> list;
        private Pagination pagination;

        public PaginatedResponse() {
        }

        public PaginatedResponse(List<?> list, Pagination pagination) {
            this.list = list;
            this.pagination = pagination;
        }

        public List<?> getList() {
            return list;
        }

        public void setList(List<?> list) {
            this.list = list;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }
    }

    /**
     * 分页信息
     */
    public static class Pagination {
        private Integer page;
        private Integer limit;
        private Integer total;
        private Integer totalPages;
        private Boolean hasPrev;
        private Boolean hasNext;

        public Pagination() {
        }

        public Pagination(Integer page, Integer limit, Integer total) {
            this.page = page;
            this.limit = limit;
            this.total = total;
            this.totalPages = (int) Math.ceil((double) total / limit);
            this.hasPrev = page > 1;
            this.hasNext = page < totalPages;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Boolean getHasPrev() {
            return hasPrev;
        }

        public void setHasPrev(Boolean hasPrev) {
            this.hasPrev = hasPrev;
        }

        public Boolean getHasNext() {
            return hasNext;
        }

        public void setHasNext(Boolean hasNext) {
            this.hasNext = hasNext;
        }
    }
}