/*
 * This file is part of WebLookAndFeel library.
 *
 * WebLookAndFeel library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * WebLookAndFeel library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with WebLookAndFeel library.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.alee.laf.filechooser;

import com.alee.managers.language.LanguageManager;
import com.alee.managers.language.LanguageMethods;
import com.alee.managers.language.updaters.LanguageUpdater;
import com.alee.managers.style.*;
import com.alee.painter.Paintable;
import com.alee.painter.Painter;
import com.alee.utils.CollectionUtils;
import com.alee.utils.FileUtils;
import com.alee.utils.ImageUtils;
import com.alee.utils.SwingUtils;
import com.alee.utils.swing.Customizer;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * {@link JFileChooser} extension class.
 * It contains various useful methods to simplify core component usage.
 * <p/>
 * This component should never be used with a non-Web UIs as it might cause an unexpected behavior.
 * You could still use that component even if WebLaF is not your application L&amp;F as this component will use Web-UI in any case.
 *
 * @author Mikle Garin
 * @see JFileChooser
 * @see WebFileChooserUI
 * @see FileChooserPainter
 */

public class WebFileChooser extends JFileChooser
        implements Styleable, Paintable, ShapeMethods, MarginMethods, PaddingMethods, LanguageMethods
{
    /**
     * Custom icons for file chooser dialog.
     */
    protected List<? extends Image> customIcons = null;

    /**
     * Constructs a WebFileChooser pointing to the user's default directory.
     */
    public WebFileChooser ()
    {
        this ( StyleId.auto );
    }

    /**
     * Constructs a WebFileChooser using the given path.
     * Passing in a null string causes the file chooser to point to the user's default directory.
     *
     * @param dirPath a String giving the path to a file or directory
     */
    public WebFileChooser ( final String dirPath )
    {
        this ( StyleId.auto, dirPath );
    }

    /**
     * Constructs a WebFileChooser using the given File as the path.
     * Passing in a null file causes the file chooser to point to the user's default directory.
     *
     * @param dir a File object specifying the path to a file or directory
     */
    public WebFileChooser ( final File dir )
    {
        this ( StyleId.auto, dir );
    }

    /**
     * Constructs a WebFileChooser using the given FileSystemView.
     *
     * @param fsv file system view
     */
    public WebFileChooser ( final FileSystemView fsv )
    {
        this ( StyleId.auto, fsv );
    }

    /**
     * Constructs a WebFileChooser using the given current directory and FileSystemView.
     *
     * @param dir a File object specifying the path to a file or directory
     * @param fsv file system view
     */
    public WebFileChooser ( final File dir, final FileSystemView fsv )
    {
        this ( StyleId.auto, dir, fsv );
    }

    /**
     * Constructs a WebFileChooser using the given current directory path and FileSystemView.
     *
     * @param dirPath a String giving the path to a file or directory
     * @param fsv     file system view
     */
    public WebFileChooser ( final String dirPath, final FileSystemView fsv )
    {
        this ( StyleId.auto, dirPath, fsv );
    }

    /**
     * Constructs a WebFileChooser pointing to the user's default directory.
     *
     * @param id style ID
     */
    public WebFileChooser ( final StyleId id )
    {
        this ( id, FileUtils.getUserHomePath (), null );
    }

    /**
     * Constructs a WebFileChooser using the given path.
     * Passing in a null string causes the file chooser to point to the user's default directory.
     *
     * @param id      style ID
     * @param dirPath a String giving the path to a file or directory
     */
    public WebFileChooser ( final StyleId id, final String dirPath )
    {
        this ( id, dirPath, null );
    }

    /**
     * Constructs a WebFileChooser using the given File as the path.
     * Passing in a null file causes the file chooser to point to the user's default directory.
     *
     * @param id  style ID
     * @param dir a File object specifying the path to a file or directory
     */
    public WebFileChooser ( final StyleId id, final File dir )
    {
        this ( id, dir, null );
    }

    /**
     * Constructs a WebFileChooser using the given FileSystemView.
     *
     * @param id  style ID
     * @param fsv file system view
     */
    public WebFileChooser ( final StyleId id, final FileSystemView fsv )
    {
        this ( id, ( File ) null, fsv );
    }

    /**
     * Constructs a WebFileChooser using the given current directory and FileSystemView.
     *
     * @param id  style ID
     * @param dir a File object specifying the path to a file or directory
     * @param fsv file system view
     */
    public WebFileChooser ( final StyleId id, final File dir, final FileSystemView fsv )
    {
        super ( dir, fsv );
        setStyleId ( id );
    }

    /**
     * Constructs a WebFileChooser using the given current directory path and FileSystemView.
     *
     * @param id      style ID
     * @param dirPath a String giving the path to a file or directory
     * @param fsv     file system view
     */
    public WebFileChooser ( final StyleId id, final String dirPath, final FileSystemView fsv )
    {
        super ( dirPath, fsv );
        setStyleId ( id );
    }

    @Override
    protected JDialog createDialog ( final Component parent ) throws HeadlessException
    {
        final JDialog dialog = super.createDialog ( parent );
        if ( customIcons != null )
        {
            dialog.setIconImages ( customIcons );
        }
        return dialog;
    }

    /**
     * Returns custom dialog icon.
     *
     * @return custom dialog icon
     */
    public Image getDialogIcon ()
    {
        return customIcons != null && customIcons.size () > 0 ? customIcons.get ( 0 ) : null;
    }

    /**
     * Returns custom dialog icons.
     *
     * @return custom dialog icons
     */
    public List<? extends Image> getDialogIcons ()
    {
        return customIcons;
    }

    /**
     * Sets custom dialog icon.
     *
     * @param icon new custom dialog icon
     */
    public void setDialogIcon ( final ImageIcon icon )
    {
        setDialogImage ( icon.getImage () );
    }

    /**
     * Sets custom dialog icon.
     *
     * @param icon new custom dialog icon
     */
    public void setDialogImage ( final Image icon )
    {
        setDialogImages ( CollectionUtils.asList ( icon ) );
    }

    /**
     * Sets custom dialog icons.
     *
     * @param customIcons new custom dialog icons
     */
    public void setDialogIcons ( final List<? extends ImageIcon> customIcons )
    {
        setDialogImages ( ImageUtils.toImagesList ( customIcons ) );
    }

    /**
     * Sets custom dialog icons.
     *
     * @param customIcons new custom dialog icons
     */
    public void setDialogImages ( final List<? extends Image> customIcons )
    {
        this.customIcons = customIcons;

        // Updating icon on displayed dialog
        final Window window = SwingUtils.getWindowAncestor ( this );
        if ( window != null && window instanceof JDialog )
        {
            window.setIconImages ( customIcons );
        }
    }

    /**
     * Sets dialog title language key.
     *
     * @param dialogTitle title language key
     */
    public void setDialogTitleKey ( final String dialogTitle )
    {
        setLanguage ( dialogTitle );
    }

    /**
     * Sets currently displayed directory.
     *
     * @param path directory to display
     */
    public void setCurrentDirectory ( final String path )
    {
        setCurrentDirectory ( path != null ? new File ( path ) : null );
    }

    /**
     * Sets currently selected file.
     *
     * @param path file to select
     */
    public void setSelectedFile ( final String path )
    {
        setSelectedFile ( path != null ? new File ( path ) : null );
    }
    
    /**
    * Sets currently selected file.
    *
    * @param the file to select
    */
    public void setSelectedFile ( final File file )
    {
    	if ( file == null || file.exists() )
    	{
    		super.setSelectedFile ( file );
    	}
    	else
    	{
    		this.getFileChooserPanel ().getSelectedFilesTextField ().setText ( file.getName () );
    		if ( file.getParent() != null && new File ( file.getParent () ).exists () ) 
    		{
    			this.setCurrentDirectory( file.getParent () );
    		}
    	}
    }

    /**
     * Returns file chooser panel.
     *
     * @return file chooser panel
     */
    public WebFileChooserPanel getFileChooserPanel ()
    {
        return getUI ().getFileChooserPanel ();
    }

    @Override
    public StyleId getDefaultStyleId ()
    {
        return StyleId.filechooser;
    }

    @Override
    public StyleId getStyleId ()
    {
        return StyleManager.getStyleId ( this );
    }

    @Override
    public StyleId setStyleId ( final StyleId id )
    {
        return StyleManager.setStyleId ( this, id );
    }

    @Override
    public StyleId resetStyleId ()
    {
        return StyleManager.resetStyleId ( this );
    }

    @Override
    public Skin getSkin ()
    {
        return StyleManager.getSkin ( this );
    }

    @Override
    public Skin setSkin ( final Skin skin )
    {
        return StyleManager.setSkin ( this, skin );
    }

    @Override
    public Skin setSkin ( final Skin skin, final boolean recursively )
    {
        return StyleManager.setSkin ( this, skin, recursively );
    }

    @Override
    public Skin resetSkin ()
    {
        return StyleManager.resetSkin ( this );
    }

    @Override
    public void addStyleListener ( final StyleListener listener )
    {
        StyleManager.addStyleListener ( this, listener );
    }

    @Override
    public void removeStyleListener ( final StyleListener listener )
    {
        StyleManager.removeStyleListener ( this, listener );
    }

    @Override
    public Map<String, Painter> getCustomPainters ()
    {
        return StyleManager.getCustomPainters ( this );
    }

    @Override
    public Painter getCustomPainter ()
    {
        return StyleManager.getCustomPainter ( this );
    }

    @Override
    public Painter getCustomPainter ( final String id )
    {
        return StyleManager.getCustomPainter ( this, id );
    }

    @Override
    public Painter setCustomPainter ( final Painter painter )
    {
        return StyleManager.setCustomPainter ( this, painter );
    }

    @Override
    public Painter setCustomPainter ( final String id, final Painter painter )
    {
        return StyleManager.setCustomPainter ( this, id, painter );
    }

    @Override
    public boolean resetPainter ()
    {
        return StyleManager.resetPainter ( this );
    }

    @Override
    public Shape getShape ()
    {
        return ShapeMethodsImpl.getShape ( this );
    }

    @Override
    public Insets getMargin ()
    {
        return MarginMethodsImpl.getMargin ( this );
    }

    @Override
    public void setMargin ( final int margin )
    {
        MarginMethodsImpl.setMargin ( this, margin );
    }

    @Override
    public void setMargin ( final int top, final int left, final int bottom, final int right )
    {
        MarginMethodsImpl.setMargin ( this, top, left, bottom, right );
    }

    @Override
    public void setMargin ( final Insets margin )
    {
        MarginMethodsImpl.setMargin ( this, margin );
    }

    @Override
    public Insets getPadding ()
    {
        return PaddingMethodsImpl.getPadding ( this );
    }

    @Override
    public void setPadding ( final int padding )
    {
        PaddingMethodsImpl.setPadding ( this, padding );
    }

    @Override
    public void setPadding ( final int top, final int left, final int bottom, final int right )
    {
        PaddingMethodsImpl.setPadding ( this, top, left, bottom, right );
    }

    @Override
    public void setPadding ( final Insets padding )
    {
        PaddingMethodsImpl.setPadding ( this, padding );
    }

    /**
     * Returns the look and feel (L&amp;F) object that renders this component.
     *
     * @return the {@link WFileChooserUI} object that renders this component
     */
    @Override
    public WFileChooserUI getUI ()
    {
        return ( WFileChooserUI ) super.getUI ();
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * @param ui {@link WFileChooserUI}
     */
    public void setUI ( final WFileChooserUI ui )
    {
        super.setUI ( ui );
    }

    @Override
    public void updateUI ()
    {
        StyleManager.getDescriptor ( this ).updateUI ( this );
    }

    @Override
    public String getUIClassID ()
    {
        return StyleManager.getDescriptor ( this ).getUIClassId ();
    }

    @Override
    public String getLanguage ()
    {
        return LanguageManager.getComponentKey ( this );
    }

    @Override
    public void setLanguage ( final String key, final Object... data )
    {
        LanguageManager.registerComponent ( this, key, data );
    }

    @Override
    public void updateLanguage ( final Object... data )
    {
        LanguageManager.updateComponent ( this, data );
    }

    @Override
    public void updateLanguage ( final String key, final Object... data )
    {
        LanguageManager.updateComponent ( this, key, data );
    }

    @Override
    public void removeLanguage ()
    {
        LanguageManager.unregisterComponent ( this );
    }

    @Override
    public boolean isLanguageSet ()
    {
        return LanguageManager.isRegisteredComponent ( this );
    }

    @Override
    public void setLanguageUpdater ( final LanguageUpdater updater )
    {
        LanguageManager.registerLanguageUpdater ( this, updater );
    }

    @Override
    public void removeLanguageUpdater ()
    {
        LanguageManager.unregisterLanguageUpdater ( this );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @return selected file
     */
    public static File showOpenDialog ()
    {
        return showOpenDialog ( null, null, null );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @param customizer file chooser customizer
     * @return selected file
     */
    public static File showOpenDialog ( final Customizer<WebFileChooser> customizer )
    {
        return showOpenDialog ( null, null, customizer );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @param parent     parent component
     * @param customizer file chooser customizer
     * @return selected file
     */
    public static File showOpenDialog ( final Component parent, final Customizer<WebFileChooser> customizer )
    {
        return showOpenDialog ( parent, null, customizer );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected file
     */
    public static File showOpenDialog ( final String currentDirectory, final Customizer<WebFileChooser> customizer )
    {
        return showOpenDialog ( null, currentDirectory, customizer );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @return selected file
     */
    public static File showOpenDialog ( final Component parent, final String currentDirectory )
    {
        return showOpenDialog ( parent, currentDirectory, null );
    }

    /**
     * Constructs and displays file open dialog and returns selected file as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected file
     */
    public static File showOpenDialog ( final Component parent, final String currentDirectory, final Customizer<WebFileChooser> customizer )
    {
        final WebFileChooser fileChooser = new WebFileChooser ( currentDirectory );
        fileChooser.setMultiSelectionEnabled ( false );
        if ( customizer != null )
        {
            customizer.customize ( fileChooser );
        }
        if ( fileChooser.showOpenDialog ( parent ) == APPROVE_OPTION )
        {
            return fileChooser.getSelectedFile ();
        }
        else
        {
            return null;
        }
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ()
    {
        return showMultiOpenDialog ( null, null, null );
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @param customizer file chooser customizer
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ( final Customizer<WebFileChooser> customizer )
    {
        return showMultiOpenDialog ( null, null, customizer );
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @param parent     parent component
     * @param customizer file chooser customizer
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ( final Component parent, final Customizer<WebFileChooser> customizer )
    {
        return showMultiOpenDialog ( parent, null, customizer );
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ( final String currentDirectory, final Customizer<WebFileChooser> customizer )
    {
        return showMultiOpenDialog ( null, currentDirectory, customizer );
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ( final Component parent, final String currentDirectory )
    {
        return showMultiOpenDialog ( parent, currentDirectory, null );
    }

    /**
     * Constructs and displays multiply files open dialog and returns selected files list as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected files list
     */
    public static List<File> showMultiOpenDialog ( final Component parent, final String currentDirectory,
                                                   final Customizer<WebFileChooser> customizer )
    {
        final WebFileChooser fileChooser = new WebFileChooser ( currentDirectory );
        fileChooser.setMultiSelectionEnabled ( true );
        if ( customizer != null )
        {
            customizer.customize ( fileChooser );
        }
        if ( fileChooser.showOpenDialog ( parent ) == APPROVE_OPTION )
        {
            return CollectionUtils.toList ( fileChooser.getSelectedFiles () );
        }
        else
        {
            return null;
        }
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @return selected file
     */
    public static File showSaveDialog ()
    {
        return showSaveDialog ( null, null, null );
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @param customizer file chooser customizer
     * @return selected file
     */
    public static File showSaveDialog ( final Customizer<WebFileChooser> customizer )
    {
        return showSaveDialog ( null, null, customizer );
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @param parent     parent component
     * @param customizer file chooser customizer
     * @return selected file
     */
    public static File showSaveDialog ( final Component parent, final Customizer<WebFileChooser> customizer )
    {
        return showSaveDialog ( parent, null, customizer );
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected file
     */
    public static File showSaveDialog ( final String currentDirectory, final Customizer<WebFileChooser> customizer )
    {
        return showSaveDialog ( null, currentDirectory, customizer );
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @return selected file
     */
    public static File showSaveDialog ( final Component parent, final String currentDirectory )
    {
        return showSaveDialog ( parent, currentDirectory, null );
    }

    /**
     * Constructs and displays file save dialog and returns selected file as a result.
     *
     * @param parent           parent component
     * @param currentDirectory current file chooser directory
     * @param customizer       file chooser customizer
     * @return selected file
     */
    public static File showSaveDialog ( final Component parent, final String currentDirectory, final Customizer<WebFileChooser> customizer )
    {
        final WebFileChooser fileChooser = new WebFileChooser ( currentDirectory );
        fileChooser.setMultiSelectionEnabled ( true );
        if ( customizer != null )
        {
            customizer.customize ( fileChooser );
        }
        if ( fileChooser.showSaveDialog ( parent ) == APPROVE_OPTION )
        {
            return fileChooser.getSelectedFile ();
        }
        else
        {
            return null;
        }
    }
}