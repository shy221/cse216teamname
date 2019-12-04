// a global for the ShowQR of the program.  See newEntryForm for 
// explanation
//var ShowQR: ShowQR;

/**
 * ShowQR encapsulates all of the code for the form for editing an entry
 */
class ShowQR {

    private static readonly NAME = "ShowQR";

    /**
     * Track if the Singleton has been initialized
     */
    private static isInit = false;

    /**
     * init() is called from an AJAX GET, and should populate the form if and 
     * only if the GET did not have an error
     */
    private static init() {
        if (!ShowQR.isInit) {

            $("body").prepend(Handlebars.templates[Navbar.NAME + ".hb"]());
            $("#"+Navbar.NAME+"-showQR").click(Navbar.login);
            $("#" + ShowQR.NAME + "-closebtn").click(ShowQR.hide);
            ShowQR.isInit = true;

        }
    }

    private static reload() {
        let id = "" + $("#" + ShowQR.NAME + "-detailId").val();
        // Issue a GET, and then pass the result to update()
        $.ajax({
            type: "GET",
            url: "/messages/" + id,
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: ShowQR.init
        });
    }

    
    /**
     * Refresh() doesn't really have much meaning, but just like in sNavbar, we
     * have a refresh() method so that we don't have front-end code calling
     * init().
     */
    public static refresh() {
        ShowQR.init();
    }

    /**
     * update() is the private method used by refresh() to update the 
     * ShowQR
     */
    private static update(data: any) {
        // Remove the data, if it exists
        //$("#" + ShowQR.NAME).remove();
        $("body").append(Handlebars.templates[ShowQR.NAME + ".hb"](data));
        $("#" + ShowQR.NAME + "-userprofilebtn").click(ShowQR.other);
        $("#" + ShowQR.NAME + "-closebtn").click(ShowQR.hide);
    }

    /**
     * Hide the ShowQR.  Be sure to clear its fields first
     */
    private static hide() {
        
        $("#" + ShowQR.NAME + "-title").val("");
        $("#" + ShowQR.NAME).modal("hide");
    }

    /**
     * Show the ShowQR.  Be sure to clear its fields, because there are
     * ways of making a Bootstrap modal disapper without clicking Close, and
     * we haven't set up the hooks to clear the fields on the events associated
     * with those ways of making the modal disappear.
     */
    public static show(data: any) {
        $("#" + ShowQR.NAME + "-detailId").val(data.mData.mId);
        $("#" + ShowQR.NAME).modal("show");
    }


    private static other() {
        let uid = "" + $("#" + ShowQR.NAME + "-detailPostUid").val();
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userprofile",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: UserProfile.showBasic
        });
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userposts",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: UserProfile.showPosts
        });
    }
} // end class ShowQR