package org.mix3.blog;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.mix3.blog.page.TopPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(TopPage.class);

		//assert rendered page class
//		tester.assertRenderedPage(TopPage.class);

		//assert rendered label component
//		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
