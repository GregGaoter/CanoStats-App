import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { IMouvementsStock, defaultValue } from 'app/shared/model/mouvements-stock.model';
import { EntityState, createEntitySlice, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { cleanEntity } from 'app/shared/util/entity-utils';
import axios from 'axios';

const initialState: EntityState<IMouvementsStock> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export const apiUrl = 'api/mouvements-stocks';

// Actions

export const getEntities = createAsyncThunk(
  'mouvementsStock/fetch_entity_list',
  async (queryParams: string) => {
    const requestUrl = `${apiUrl}?${queryParams ? queryParams : ''}`;
    return axios.get<IMouvementsStock[]>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const getEntity = createAsyncThunk(
  'mouvementsStock/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IMouvementsStock>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'mouvementsStock/create_entity',
  async (entity: IMouvementsStock, thunkAPI) => {
    const result = await axios.post<IMouvementsStock>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities(undefined));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'mouvementsStock/update_entity',
  async (entity: IMouvementsStock, thunkAPI) => {
    const result = await axios.put<IMouvementsStock>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities(undefined));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'mouvementsStock/partial_update_entity',
  async (entity: IMouvementsStock, thunkAPI) => {
    const result = await axios.patch<IMouvementsStock>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities(undefined));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'mouvementsStock/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IMouvementsStock>(requestUrl);
    thunkAPI.dispatch(getEntities(undefined));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const MouvementsStockSlice = createEntitySlice({
  name: 'mouvementsStock',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data,
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = MouvementsStockSlice.actions;

// Reducer
export default MouvementsStockSlice.reducer;
