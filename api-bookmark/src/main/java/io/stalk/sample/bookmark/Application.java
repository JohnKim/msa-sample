package io.stalk.sample.bookmark;

import io.stalk.sample.bookmark.model.Account;
import io.stalk.sample.bookmark.model.AccountRepository;
import io.stalk.sample.bookmark.model.Bookmark;
import io.stalk.sample.bookmark.model.BookmarkRepository;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application implements CommandLineRunner {
	
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	@Value("${app.version}")
	private String version;
	
	@Autowired AccountRepository accountRepository;
	@Autowired BookmarkRepository bookmarkRepository;

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		
		if(logger.isDebugEnabled()) {
			logger.debug("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
	        Arrays.sort(beanNames);
	        for (String beanName : beanNames) {
	        	logger.debug(beanName);
	        }
		}
        
	}

	@Override
	public void run(String... arg0) throws Exception {
		Arrays
		.asList( "jhoeller,dsyer,pwebb,ogierke,rwinch,mfisher,mpollack,jlong".split(","))
		.forEach(a -> {
			Account account = accountRepository.save(new Account(a,
					"password"));

			bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/1/" + a, "A description"));
			bookmarkRepository.save(new Bookmark(account, "http://bookmark.com/2/" + a, "A description"));

		});
		
		logger.info("Started on version "+version);
	}
}
