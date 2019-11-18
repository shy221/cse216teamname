<div id="EditEntryForm" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Edit a Post</h4>
            </div>
            <div class="modal-body">
                <label for="EditEntryForm-title">Event Title</label>
                <input class="form-control" type="text" id="EditEntryForm-title" />
                <label for="EditEntryForm-message">Event Detail</label>
                <textarea class="form-control" id="EditEntryForm-message"></textarea>
                <label for="EditEntryForm-link">Link</label>
                <input class="form-control" type="text" id="EditEntryForm-link" />
                <label for="EditEntryForm-attachment">Attachment</label>
                <input class="form-control-file" type="file" accept=".pdf" id="EditEntryForm-attachment" />
                <label for="EditEntryForm-created">Time Created</label>
                <span id="EditEntryForm-created"></span>
                <input type="hidden" id="EditEntryForm-editId" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="EditEntryForm-Update">Update</button>
                <button type="button" class="btn btn-default" id="EditEntryForm-Cancel">Cancel</button>
            </div>
        </div>
    </div>
</div>
