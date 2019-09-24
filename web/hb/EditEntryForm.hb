<div id="EditEntryForm" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Edit an Entry</h4>
            </div>
            <div class="modal-body">
                <label for="EditEntryForm-title">Title</label>
                <input class="form-control" type="text" id="EditEntryForm-title" />
                <label for="EditEntryForm-message">Message</label>
                <textarea class="form-control" id="EditEntryForm-message"></textarea>
                <label for="EditEntryForm-created">Event created</label>
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
