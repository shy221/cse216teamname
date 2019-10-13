class UserProfile {
    private static readonly NAME = "UserProfile";
    private static isInit = false;

    private static init() {
        if (!UserProfile.isInit) {
            $("body").append(Handlebars.templates[UserProfile.NAME + ".hb"]());
            $("#" + UserProfile.NAME + "-Close").click(UserProfile.hide);
            UserProfile.isInit = true;
        }
    }

    public static refresh() {
        UserProfile.init();
    }

    private static hide() {
        $("#" + UserProfile.NAME + "-id").val("");
        $("#" + UserProfile.NAME + "-name").text("");
        $("#" + UserProfile.NAME + "-email").text("");
        $("#" + UserProfile.NAME + "-intro").text("");
        $("#" + UserProfile.NAME).modal("hide");
    }

    public static show(data:any) {
        $("#" + UserProfile.NAME + "-id").val(data.mData.uId);
        $("#" + UserProfile.NAME + "-name").text(data.mData.uSername);
        $("#" + UserProfile.NAME + "-email").text(data.mData.uEmail);
        $("#" + UserProfile.NAME + "-intro").text(data.mData.uIntro);
        $("#" + UserProfile.NAME).modal("show");
    }
    
    public static get(){
        $.ajax({
            type: "POST",
            url: "/" + uid,
            dataType: "json",
            data: JSON.stringify({ uEmail: uemail, sessionKey: ukey }),
            success: UserProfile.show
        });
    }
}