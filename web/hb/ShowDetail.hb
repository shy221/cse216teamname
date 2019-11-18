<div id="ShowDetail" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title" id="ShowDetail-title"></h3>
                <button type="button" class="btn btn-default" id="ShowDetail-closebtn" style="float: right; position: relative">X</button>
            </div>
            <div class="modal-body">
                <div class="user-face">
                    <img class="user-head" src="//static.hdslb.com/images/member/noface.gif" style="width:10%">
                    <h5 class="user-name" id="ShowDetail-username"></h5>
                </div>
                <button type="button" class="btn btn-default" id="ShowDetail-userprofilebtn">user</button>
                <hr>
                <label for="ShowDetail-message">Details</label>
                <textarea class="form-control" id="ShowDetail-message"></textarea>
                <label for="ShowDetail-link">Link</label>
                <a id="ShowDetail-link"></a>
                <br>
                <br>
                <label for="ShowDetail-attachment">Attachment</label>
                <br>
                <iframe width="100%" id="ShowDetail-attachment"></iframe>
                <br>
                <label for="ShowDetail-created">Event created</label>
                <span id="ShowDetail-created"></span>
                <input type="hidden" id="ShowDetail-detailId" />
                <input type="hidden" id="ShowDetail-detailPostUid" />
                <hr>
                <label for="ShowDetail-postcomments">Comment</label>
                <input class="form-control" type="text" id="ShowDetail-postcomments"></textarea>
                <label for="ShowDetail-postcomments-link">Comment-Link</label>
                <input class="form-control" type="text" id="ShowDetail-postcomments-link" />
                <label for="ShowDetail-postcomments-attachment">Comment-File</label>
                <input class="form-control-file" type="file" accept=".pdf" id="ShowDetail-postcomments-attachment" />
                <br>
                <button type="button" class="btn btn-default" id="ShowDetail-commentsbtn">Leave Comments</button>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowDetail-scommentsbtn">List All Comments</button>
                <button type="button" class="btn btn-default" id="ShowDetail-likebtn"></button>
                <button type="button" class="btn btn-default" id="ShowDetail-dislikebtn"></button>
                <button type="button" class="btn btn-default" id="ShowDetail-editbtn">Edit</button>
                <button type="button" class="btn btn-default" id="ShowDetail-delbtn">Delete</button>
            </div>
        </div>
    </div>
</div>
