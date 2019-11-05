/**
 * The Navbar Singleton is the navigation bar at the top of the page.  Through 
 * its HTML, it is designed so that clicking the "brand" part will refresh the
 * page.  Apart from that, it has an "add" button, which forwards to 
 * NewEntryForm
 */
class Navbar {
    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * The name of the DOM entry associated with Navbar
     */
    private static readonly NAME = "Navbar";

    /**
     * Initialize the Navbar Singleton by creating its element in the DOM and
     * configuring its button.  This needs to be called from any public static
     * method, to ensure that the Singleton is initialized before use.
     */
    private static init() {
        if (!Navbar.isInit) {
            $("body").prepend(Handlebars.templates[Navbar.NAME + ".hb"]());
            $("#"+Navbar.NAME+"-add").click(NewEntryForm.show);
            //$("#"+Navbar.NAME+"-login").click(Login.show);
            $("#"+Navbar.NAME+"-Account").click(UserProfile.get);
            $("#"+Navbar.NAME+"-logout").click(Navbar.logout);
            Navbar.isInit = true;
        }
    }
    public static logout() {
        var auth2 = gapi.auth2.getAuthInstance();
        auth2.signOut();
        ukey = "";
        idToken = "";
        window.location.replace("https://arcane-refuge-67249.herokuapp.com/");
        /*$.ajax({
            type: "POST",
            //not sure abour url
            url: "/logout",
            dataType: "json",
            data: JSON.stringify({"id_token": idToken}),
            success: Navbar.init
        });*/
    }

    /**
     * Refresh() doesn't really have much meaning for the navbar, but we'd 
     * rather not have anyone call init(), so we'll have this as a stub that
     * can be called during front-end initialization to ensure the navbar
     * is configured.
     */
    public static refresh() {
        Navbar.init();
    }
}