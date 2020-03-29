package design.pattern.chain;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * 测试责任链模式
 *
 */
public class Demo01_FilterChain {

    /**
     * main
     *
     */
    public static void main(String[] args) {
        FilterChain chain = new FilterChain();
        chain.addFilter(new Filter01()).addFilter(new Filter02()).addFilter(new Filter03());

        Request request = new Request("request:");
        Response response = new Response("response:");

        chain.doFilter(request, response, chain);

        Optional.ofNullable(request.getContent()).ifPresent(System.out::println);
        Optional.ofNullable(response.getContent()).ifPresent(System.out::println);
    }


    static class Request {
        private String content;
        public Request(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    static class Response {
        private String content;
        public Response(String content) {
            this.content = content;
        }
        public String getContent() {
            return content;
        }
        public void setContent(String content) {
            this.content = content;
        }
    }

    static class FilterChain implements Filter {
        private List<Filter> filters = new LinkedList<>();
        private int i = 0;

        public FilterChain addFilter(Filter filter) {
            this.filters.add(filter);
            return this;
        }

        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            if (chain.filters.isEmpty() || i==chain.filters.size()) {
                return;
            }
            Filter filter = chain.filters.get(i++);
            filter.doFilter(request, response, chain);
        }
    }

    static interface Filter {
        void doFilter(Request request, Response response, FilterChain chain);
    }

    static class Filter01 implements Filter {
        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.setContent(request.getContent() + " filter01");
            chain.doFilter(request, response, chain);
            response.setContent(response.getContent() + " filter01");
        }
    }
    static class Filter02 implements Filter {
        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.setContent(request.getContent() + " filter02");
            chain.doFilter(request, response, chain);
            response.setContent(response.getContent() + " filter02");
        }
    }
    static class Filter03 implements Filter {
        @Override
        public void doFilter(Request request, Response response, FilterChain chain) {
            request.setContent(request.getContent() + " filter03");
            chain.doFilter(request, response, chain);
            response.setContent(response.getContent() + " filter03");
        }
    }

}
