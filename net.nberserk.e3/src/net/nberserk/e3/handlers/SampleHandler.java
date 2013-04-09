package net.nberserk.e3.handlers;

import java.lang.reflect.Method;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextViewerExtension;
import org.eclipse.jface.text.TextViewer;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IEditorPart part = window.getActivePage().getActiveEditor();
		if (part instanceof AbstractTextEditor) {
			AbstractTextEditor editor = (AbstractTextEditor)part;
			Method me=null;
			try {
				me = AbstractTextEditor.class
				        .getDeclaredMethod("getSourceViewer");
				me.setAccessible(true);
	            TextViewer viewer = (TextViewer)me.invoke(editor);
	            if (viewer != null) {
	                                
	                StyledText st= viewer.getTextWidget();
	        		if (st != null){
	        			// compute the number of lines displayed
	            		int height= st.getClientArea().height;
	            		int lineHeight= st.getLineHeight();
	            		int half = height/(lineHeight*2); // #line of half screen

	            		int caretOffset= st.getCaretOffset();
	            		int caretLine= st.getLineAtOffset(caretOffset);
	            		int totalCount = st.getLineCount();

	            		int screenTop = caretLine;
	            		int center = Math.max(0, caretLine - half);
	            		int screenBottom = Math.max(0, caretLine-(half*2));
	            		
	            		System.out.println(String.format("maxLine=%1d, half=%d, top=%2d, center=%3d, bottom=%4d, caretLine=%5d", totalCount, half, screenTop, center, screenBottom, caretLine));
	            			            		
	            		
	            		
	            		if (caretLine == center) {
	            			st.setTopIndex(caretLine);
						}else if(caretLine == screenBottom){
							st.setTopIndex(center);
						}else{
							st.setTopIndex(screenBottom);
						}
	        		}        		        		
	            }

			} catch (Exception e) {
				e.printStackTrace();
			}
            		}
		return null;
	}
}
