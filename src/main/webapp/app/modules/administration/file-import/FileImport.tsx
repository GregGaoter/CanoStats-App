import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Icon } from 'app/shared/component/Icon';
import { Button } from 'primereact/button';
import { Card } from 'primereact/card';
import {
  FileUpload,
  FileUploadHeaderTemplateOptions,
  FileUploadOptions,
  FileUploadSelectEvent,
  FileUploadUploadEvent,
  ItemTemplateOptions,
} from 'primereact/fileupload';
import { ProgressBar } from 'primereact/progressbar';
import { Tag } from 'primereact/tag';
import { Tooltip } from 'primereact/tooltip';
import React, { useRef, useState } from 'react';

export const FileImport = () => {
  const [totalSize, setTotalSize] = useState<number>(0);
  const fileUploadRef = useRef<FileUpload>(null);

  const onTemplateSelect = (e: FileUploadSelectEvent) => {
    let _totalSize = totalSize;
    const files = e.files;
    for (let i = 0; i < files.length; i++) {
      _totalSize += files[i].size || 0;
    }
    setTotalSize(_totalSize);
  };

  const onTemplateUpload = (e: FileUploadUploadEvent) => {
    let _totalSize = 0;
    e.files.forEach(file => {
      _totalSize += file.size || 0;
    });
    setTotalSize(_totalSize);
    window.showToast('info', 'Information', 'Fichier téléchargé');
  };

  const onTemplateRemove = (file: File, callback: any) => {
    setTotalSize(totalSize - file.size);
    callback();
  };

  const onTemplateClear = () => {
    setTotalSize(0);
  };

  const headerTemplate = (options: FileUploadHeaderTemplateOptions) => {
    const { className, chooseButton, uploadButton, cancelButton } = options;
    const value: number = fileUploadRef && fileUploadRef.current ? parseFloat(`${fileUploadRef.current.formatSize(totalSize)}`) : 0;
    const formatedValue: string = value === 0 ? '0 B' : value.toFixed(1).concat(' MB');

    return (
      <div className={className} style={{ backgroundColor: 'transparent', display: 'flex', alignItems: 'center' }}>
        {chooseButton}
        {uploadButton}
        {cancelButton}
        <div className="flex align-items-center gap-3 ml-auto text-color-secondary">
          <span>{formatedValue} / 50 MB</span>
          <ProgressBar value={2 * value} showValue={false} style={{ width: '10rem', height: '12px' }}></ProgressBar>
        </div>
      </div>
    );
  };

  const itemTemplate = (inFile: object, props: ItemTemplateOptions) => {
    const file = inFile as File;
    return (
      <div className="flex align-items-center justify-content-between text-color-secondary">
        <div className="flex align-items-center gap-3">
          <img src="content/images/fichier-json.png" height="40"></img>
          {file.name}
          <Tag value={props.formatSize} severity="info" />
        </div>
        <Button
          type="button"
          icon={<FontAwesomeIcon icon="xmark" />}
          className="p-button-outlined p-button-rounded p-button-danger ml-auto"
          onClick={() => onTemplateRemove(file, props.onRemove)}
        />
      </div>
    );
  };

  const emptyTemplate = () => {
    return (
      <div className="flex align-items-center flex-column">
        <FontAwesomeIcon
          icon="image"
          className="mt-3 p-5"
          style={{ fontSize: '5em', borderRadius: '50%', backgroundColor: 'var(--surface-b)', color: 'var(--surface-d)' }}
        />
        <span style={{ fontSize: '1.2em', color: 'var(--text-color-secondary)' }} className="my-5">
          Glissez et déposez le fichier JSON ici
        </span>
      </div>
    );
  };

  const chooseOptions: FileUploadOptions = {
    icon: <Icon icon="image" marginRight={false} />,
    iconOnly: true,
    className: 'custom-choose-btn p-button-rounded p-button-outlined',
  };
  const uploadOptions = {
    icon: <Icon icon="cloud-arrow-up" marginRight={false} />,
    iconOnly: true,
    className: 'custom-upload-btn p-button-success p-button-rounded p-button-outlined',
  };
  const cancelOptions = {
    icon: <Icon icon="xmark" marginRight={false} />,
    iconOnly: true,
    className: 'custom-cancel-btn p-button-danger p-button-rounded p-button-outlined',
  };

  return (
    <Card title="Importer des données de mouvements de stock">
      <Tooltip target=".custom-choose-btn" content="Choisir" position="bottom" />
      <Tooltip target=".custom-upload-btn" content="Télécharger" position="bottom" />
      <Tooltip target=".custom-cancel-btn" content="Effacer" position="bottom" />
      <FileUpload
        ref={fileUploadRef}
        name="mouvementsStocksFile"
        url="/api/mouvements-stocks/import"
        multiple={false}
        accept="application/json"
        maxFileSize={50000000}
        onUpload={onTemplateUpload}
        onSelect={onTemplateSelect}
        onError={onTemplateClear}
        onClear={onTemplateClear}
        headerTemplate={headerTemplate}
        itemTemplate={itemTemplate}
        emptyTemplate={emptyTemplate}
        chooseOptions={chooseOptions}
        uploadOptions={uploadOptions}
        cancelOptions={cancelOptions}
      />
    </Card>
  );
};
