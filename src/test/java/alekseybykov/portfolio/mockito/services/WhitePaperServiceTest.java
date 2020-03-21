package alekseybykov.portfolio.mockito.services;

import alekseybykov.portfolio.mockito.dependencies.external.DocumentService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WhitePaperServiceTest {

	private static List<String> whitePapers;

	@BeforeClass
	public static void setup() {
		whitePapers = Arrays.asList("Document1 rev.01", "Document2 rev.02", "Document1 rev.02", "Document4 rev.03");
	}

	@Test
	public void testFilterWhitePapers() {
		DocumentService documentService = mock(DocumentService.class);
		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		WhitePaperService whitePaperService = new WhitePaperService(documentService);
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		assertEquals(2, whitePapers.size());
	}

	@Test
	public void testDeleteWhitePapers() {
		DocumentService documentService = mock(DocumentService.class);

		String whitePaperName = "Document1";

		when(documentService.searchDocumentsByName(whitePaperName)).thenReturn(whitePapers);

		WhitePaperService whitePaperService = new WhitePaperService(documentService);

		whitePaperService.deleteWhitePapers(whitePaperName);

		verify(documentService).deleteDocumentByName("Document1 rev.01");
		verify(documentService).deleteDocumentByName("Document1 rev.02");

		verify(documentService, Mockito.times(1)).deleteDocumentByName("Document1 rev.01");
		verify(documentService, Mockito.times(1)).deleteDocumentByName("Document1 rev.02");

		verify(documentService, Mockito.never()).deleteDocumentByName("Document2 rev.02");
		verify(documentService, Mockito.never()).deleteDocumentByName("Document4 rev.03");
	}

	@Test
	public void testFilterWhitePapersUsingBddStyle() {
		DocumentService documentService = mock(DocumentService.class);
		WhitePaperService whitePaperService = new WhitePaperService(documentService);
		
		String whitePaperName = "Document2";

		//given
		given(documentService.searchDocumentsByName(whitePaperName)).willReturn(whitePapers);

		//when
		List<String> whitePapers = whitePaperService.filterWhitePapers(whitePaperName);

		//then
		assertThat(whitePapers.size(), is(1));
	}
}
