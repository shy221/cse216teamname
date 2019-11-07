class UserProfile {
    private static readonly NAME = "UserProfile";
    private static isInit = false;

    private static init() {
        if (!UserProfile.isInit) {
            $("body").append(Handlebars.templates[UserProfile.NAME + ".hb"]());
            $("#" + UserProfile.NAME + "-Close").click(UserProfile.hide);
            $("#" + UserProfile.NAME + "-editbtn").click(UserProfile.clickEdit);
            
            $("#" + UserProfile.NAME + "-showposts").click(UserProfile.showPosts);
            UserProfile.isInit = true;
        }
    }

    public static refresh() {
        UserProfile.init();
    }

    private static hide() {
        $("#" + UserProfile.NAME + "-detailId").val("");
        $("#" + UserProfile.NAME + "-name").text("");
        $("#" + UserProfile.NAME + "-email").text("");
        $("#" + UserProfile.NAME + "-intro").text("");
        $("#" + UserProfile.NAME).modal("hide");
    }

    public static showBasic(data:any) {
        $("#" + UserProfile.NAME + "-detailId").val(data.mData.uId);
        console.log(data.mData.uId);
        let id = "" + $("#" + UserProfile.NAME + "-detailId").val();
        console.log(id);
        $("#" + UserProfile.NAME + "-name").text(data.mData.uSername);
        $("#" + UserProfile.NAME + "-email").text(data.mData.uEmail);
        $("#" + UserProfile.NAME + "-intro").text(data.mData.uIntro);
        $("#" + UserProfile.NAME).modal("show");
        
    }
/*    public static showPosts(data:any) {
        // Remove the table of data, if it exists
        $("#" + UserProfile.NAME).remove();
        // Use a template to re-generate the table, and then insert it
        $("body").append(Handlebars.templates[UserProfile.NAME + ".hb"](data));
        // Find rows in the table and set behavior
        // Find all of the detail buttons, and set their behavior

        console.log('show posts');
        console.log(data.mData.mLikes);
        
        $("#" + UserProfile.NAME + "-title").val(data.mData.mLikes);
        let likes = "" + $("#" + UserProfile.NAME + "-title").val();
        console.log(likes);
        $("#" + UserProfile.NAME + "-title").text(data.mData.mTitle);
        $("#" + UserProfile.NAME).modal("show");
    }*/

    //called when click Account in Navbar
    public static get(){
        
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userprofile",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: UserProfile.showBasic
        });
    }
    private static showPosts(){
        
        //let uid = "" + $("#" + "ShowDetail" + "-detailPostUid").val();
        
        $.ajax({
            type: "POST",
            url: "/" + uid + "/userposts",
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: ShowPosts.show
        });
    }

    private static clickEdit() {
        let id = "" + $("#" + UserProfile.NAME + "-detailId").val();
        if (Number(id) == uid) {
            UserProfile.hide();
            $.ajax({
                type: "POST",
                url: "/" + id + "/userprofile",
                dataType: "json",
                data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
                success: EditUserProfile.show
            });
        } else {
            window.alert("Cannot edit other's profile");
        }
    }
}